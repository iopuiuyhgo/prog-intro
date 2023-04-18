#include "matrix.h"
#include "return_codes.h"

typedef struct
{
	doubleMatrix Q;
	doubleMatrix R;
} qrPair;
typedef struct
{
	complex double a;
	complex double b;
} doubleComplexPair;

qrPair hessenbergQR(doubleMatrix matrix)
{
	doubleMatrix R = copyArray(matrix.array, matrix.x, matrix.y);
	doubleMatrix Q = copyArray(matrix.array, matrix.x, matrix.y);
	if (!R.array || !Q.array)
	{
		qrPair res;
		res.Q = Q;
		res.R = R;
		return res;
	}
	for (int i = 0; i < matrix.x - 1; ++i)
	{
		hessenbergRotateLine(Q, R, i);
	}
	qrPair res;

	res.Q = Q;
	res.R = R;

	return res;
}
doubleComplexPair eig22(double m00, double m01, double m10, double m11)
{
	complex double tr = m00 + m11;
	complex double det = m00 * m11 - m01 * m10;
	doubleComplexPair res;

	res.a = (tr + cpow(cpow(tr, 2) - 4 * det, 0.5)) / 2;
	res.b = (tr - cpow(cpow(tr, 2) - 4 * det, 0.5)) / 2;
	return res;
}

int updateMatrix(doubleMatrix *matrix, complex double *eig, int *count)
{
	if (matrix->array[matrix->x] < 1e-6)
	{
		eig[*count] = matrix->array[0];
		*count += 1;
		if (cutCross(matrix, 0, 0, 0, 0))
		{
			return 2;
		}
	}
	for (int i = 1; i < matrix->x - 2; ++i)
	{
		if (fabs(matrix->array[i * matrix->x + i - 1]) < 1e-6)
		{
			if (fabs(matrix->array[(i + 1) * matrix->x + i]) < 1e-6)
			{
				eig[*count] = matrix->array[i * matrix->x + i];
				*count += 1;
				if (cutCross(matrix, i, i, i, i))
				{
					return 2;
				}
				i -= 1;
			}
			else if (matrix->array[(i + 2) * matrix->x + i + 1] < 1e-6)
			{
				doubleComplexPair pair = eig22(
					matrix->array[i * matrix->x + i],
					matrix->array[i * matrix->x + i + 1],
					matrix->array[(i + 1) * matrix->x + i],
					matrix->array[(i + 1) * matrix->x + i + 1]);
				eig[*count] = pair.a;
				eig[*count + 1] = pair.b;
				*count += 2;
				if (cutCross(matrix, i, i, i + 1, i + 1))
				{
					return 2;
				}
				i -= 1;
			}
		}
	}
	if (matrix->array[matrix->x * matrix->y - 2] < 1e-10)
	{
		eig[*count] = matrix->array[matrix->x * matrix->y - 1];
		*count += 1;
		if (cutCross(matrix, matrix->x - 1, matrix->y - 1, matrix->x - 1, matrix->y - 1))
		{
			return 2;
		}
	}
	return 0;
}
void hessenberg_decomposition1(doubleMatrix matrix)
{
	int i, j, k;
	double cos, sin;

	for (k = 0; k < matrix.x - 2; k++)
	{
		for (i = k + 2; i < matrix.x; i++)
		{
			if (fabs(sqrt(matrix.array[(k + 1) * matrix.x + k] * matrix.array[(k + 1) * matrix.x + k] +
						  matrix.array[i * matrix.x + k] * matrix.array[i * matrix.x + k])) > 1e-10)
			{
				cos = matrix.array[(k + 1) * matrix.x + k] /
					  sqrt(matrix.array[(k + 1) * matrix.x + k] * matrix.array[(k + 1) * matrix.x + k] +
						   matrix.array[i * matrix.x + k] * matrix.array[i * matrix.x + k]);
				sin = matrix.array[i * matrix.x + k] /
					  sqrt(matrix.array[(k + 1) * matrix.x + k] * matrix.array[(k + 1) * matrix.x + k] +
						   matrix.array[i * matrix.x + k] * matrix.array[i * matrix.x + k]);
			}
			else
			{
				cos = 1;
				sin = 0;
			}

			double tmp;
			for (j = k; j < matrix.x; j++)
			{
				tmp = cos * matrix.array[(k + 1) * matrix.x + j] + sin * matrix.array[i * matrix.x + j];
				matrix.array[i * matrix.x + j] =
					-sin * matrix.array[(k + 1) * matrix.x + j] + cos * matrix.array[i * matrix.x + j];
				matrix.array[(k + 1) * matrix.x + j] = tmp;
			}
			for (j = 0; j < matrix.x; j++)
			{
				tmp = cos * matrix.array[j * matrix.x + (k + 1)] + sin * matrix.array[j * matrix.x + i];
				matrix.array[j * matrix.x + i] =
					-sin * matrix.array[j * matrix.x + (k + 1)] + cos * matrix.array[j * matrix.x + i];
				matrix.array[j * matrix.x + (k + 1)] = tmp;
			}
		}
	}
}

int ultimateEig2_0(doubleMatrix inputMatrix, complex double *res, double eps)
{
	doubleMatrix matrix = copyArray(inputMatrix.array, inputMatrix.x, inputMatrix.y);
	if (!matrix.array)
	{
		return 2;
	}
	int count = 0;

	double avgM = avg(matrix);
	scalarMultiply(matrix, 1 / avgM, matrix);
	hessenberg_decomposition1(matrix);
	for (int i = 0; i < 20000; ++i)
	{
		double shift = matrix.array[matrix.x * matrix.y - 1];
		for (int j = 0; j < matrix.x; ++j)
		{
			matrix.array[j * matrix.x + j] -= shift;
		}
		qrPair qr = hessenbergQR(matrix);
		if (!qr.R.array || !qr.Q.array)
		{
			free(matrix.array);
			if (qr.Q.array)
			{
				free(qr.Q.array);
			}
			if (qr.R.array)
			{
				free(qr.R.array);
			}
			return 2;
		}
		free(matrix.array);

		matrix = qr.Q;
		for (int j = 0; j < matrix.x; ++j)
		{
			matrix.array[j * matrix.x + j] += shift;
		}
		qr.Q.array = NULL;
		free(qr.R.array);
//		printf("%i\n", matrix.x);
		if (updateMatrix(&matrix, res, &count))
		{
			if (matrix.array)
			{
				free(matrix.array);
			}
			return 2;
		}
		if (i%10 == 0)
		{
			printf("%i\n", matrix.x);
		}
		if (matrix.x == 4)
		{
			printArray(matrix);
			break ;
		}
		if ((matrix.x < 3) && (matrix.x == 4 && matrix.array[9] < 1e-10))
		{
			break;
		}
	}
	int i = 0;
	for (; i < matrix.x - 1; ++i)
	{
		if (fabs(matrix.array[(i + 1) * matrix.x + i]) > 1e-10)
		{
			doubleComplexPair tmp =
				eig22(get(matrix, i, i), get(matrix, i, i + 1), get(matrix, i + 1, i), get(matrix, i + 1, i + 1));
			res[i + count] = tmp.a;
			res[i + count + 1] = tmp.b;
			i += 1;
		}
		else
		{
			res[i] = get(matrix, i, i);
		}
	}
	if (i < matrix.x)
	{
		res[i] = get(matrix, i, i);
	}

	for (int j = 0; j < count + matrix.x; ++j)
	{
		if (fabs(creal(res[j])) < eps)
		{
			res[j] -= creal(res[j]);
		}
		if (fabs(cimag(res[j])) < eps)
		{
			res[j] -= cimag(res[j]);
		}
		res[j] *= avgM;
	}
	free(matrix.array);
	return 0;
}

int ultimateEig(doubleMatrix inputMatrix, complex double *res, double eps)
{
	doubleMatrix matrix = copyArray(inputMatrix.array, inputMatrix.x, inputMatrix.y);
	if (!matrix.array)
	{
		return 2;
	}

	double avgM = avg(matrix);
	scalarMultiply(matrix, 1 / avgM, matrix);
	hessenberg_decomposition1(matrix);
	for (int i = 0; i < 2 * 1e6 / matrix.x; ++i)
	{
		double shift = matrix.array[matrix.x * matrix.y - 1];
		for (int j = 0; j < matrix.x; ++j)
		{
			matrix.array[j * matrix.x + j] -= shift;
		}
		qrPair qr = hessenbergQR(matrix);
		if (!qr.R.array || !qr.Q.array)
		{
			free(matrix.array);
			if (qr.Q.array)
			{
				free(qr.Q.array);
			}
			if (qr.R.array)
			{
				free(qr.R.array);
			}
			return 2;
		}
		free(matrix.array);

		matrix = qr.Q;
		for (int j = 0; j < matrix.x; ++j)
		{
			matrix.array[j * matrix.x + j] += shift;
		}
		qr.Q.array = NULL;
		free(qr.R.array);
		if (i > 1e6 / (5 * matrix.x) && underDiagSum(matrix, .001) / (matrix.x - 1) < 1e-18)
		{
			break;
		}
	}
	int i = 0;
	for (; i < matrix.x - 1; ++i)
	{
		if (fabs(matrix.array[(i + 1) * matrix.x + i]) > 1e-10)
		{
			doubleComplexPair tmp =
				eig22(get(matrix, i, i), get(matrix, i, i + 1), get(matrix, i + 1, i), get(matrix, i + 1, i + 1));
			res[i] = tmp.a;
			res[i + 1] = tmp.b;
			i += 1;
		}
		else
		{
			res[i] = get(matrix, i, i);
		}
	}
	if (i < matrix.x)
	{
		res[i] = get(matrix, i, i);
	}

	for (int j = 0; j < matrix.x; ++j)
	{
		if (fabs(creal(res[j])) < eps)
		{
			res[j] -= creal(res[j]);
		}
		if (fabs(cimag(res[j])) < eps)
		{
			res[j] -= cimag(res[j]);
		}
		res[j] *= avgM;
	}
	free(matrix.array);
	return 0;
}

qrPair QR(doubleMatrix matrix);

int main(int argc, char *argv[])
{
	if (argc != 3)
	{
		fprintf(stderr, "received %i arguments. required: 2", argc - 1);
		return ERROR_PARAMETER_INVALID;
	}
	FILE *f = fopen(argv[1], "r");
	if (!f)
	{
		fprintf(stderr, "cannot open file %s\n", argv[2]);
		return ERROR_CANNOT_OPEN_FILE;
	}
	int n;
	fscanf(f, "%i", &n);
	doubleMatrix matrix = initUndefined(n, n);
	if (!matrix.array)
	{
		fclose(f);
		fprintf(stderr, "out of memory exception\n");
		return ERROR_OUT_OF_MEMORY;
	}
	for (int j = 0; j < n * n; ++j)
	{
		fscanf(f, "%lf", &matrix.array[j]);
	}
	fclose(f);
	complex double *eig = malloc(sizeof(complex double) * n);
	if (!eig)
	{
		free(matrix.array);
		fprintf(stderr, "out of memory exception\n");
		return ERROR_OUT_OF_MEMORY;
	}

	if (matrix.x <= 100)
	{
		if (ultimateEig(matrix, eig, 1e-6) == 2)
		{
			free(matrix.array), free(eig);
			fprintf(stderr, "out of memory exception\n");
			return ERROR_OUT_OF_MEMORY;
		}
	}
	else
	{
		if (ultimateEig2_0(matrix, eig, 1e-6) == 2)
		{
			free(matrix.array), free(eig);
			fprintf(stderr, "out of memory exception\n");
			return ERROR_OUT_OF_MEMORY;
		}
	}

	f = fopen(argv[2], "w");
	if (!f)
	{
		free(eig);
		fprintf(stderr, "cannot open file %s\n", argv[2]);
		return ERROR_CANNOT_OPEN_FILE;
	}
	for (int i = 0; i < n; ++i)
	{
		fprintf(f, "%g", creal(eig[i]));
		if (cimag(eig[i]))
		{
			fprintf(f, " %+gi", cimag(eig[i]));
		}
		fprintf(f, "\n");
	}
	fclose(f);
	free(eig), free(matrix.array);
	return SUCCESS;
}

qrPair QR(doubleMatrix matrix) {
    doubleMatrix R = copyArray(matrix.array, matrix.x, matrix.y);
    doubleMatrix Q = initEye(matrix.x);
    for (int i = 0; i < matrix.x; ++i) {
        doubleMatrix u = initUndefined(1, R.y);
        doubleMatrix tmp = initUndefined(1, R.y - i);
        doubleMatrix tmp1 = initZero(1, R.y);
        doubleMatrix tmp2;

        tmp1.array[i] = 1;
        cutMatrix(R, i, 0, i+1, R.y, u);
        cutMatrix(u, 0, i, 1, R.y, tmp);
        scalarMultiply(tmp1, vectorLength(tmp), tmp1);
        subtractMatrix(u, tmp1, u);
        free(tmp1.array), free(tmp.array);

        for (int j = 0; j < i; ++j)
        {
            set(u, j, 0, 0);
        }

        tmp = initUndefined(u.y, u.x);
        transpose(u, tmp);
        tmp1 = dot(tmp, u);

        doubleMatrix p = initUndefined(matrix.x, matrix.y);
        if (tmp1.array[0] != 0)
        {
            tmp2 = dot(u, tmp);
            free(tmp.array);

            tmp = initEye(matrix.x);

            scalarMultiply(tmp2, 2 / tmp1.array[0], p);
            subtractMatrix(tmp, p, p);

            free(tmp2.array);
        }
        else
        {
            p = initEye(matrix.x);
        }
        free(tmp.array), free(tmp1.array);
        tmp = dot(p, R);
        free(R.array);
        R.array = tmp.array;
        tmp.array = NULL;


        tmp1 = dot(Q, p);
        free(Q.array);
        Q.array = tmp1.array;
        tmp1.array = NULL;

        for (int j = i+1; j < matrix.x; ++j) {
            set(R, j, i, 0);
        }

        free(u.array), free(p.array);
    }
    qrPair res;
    res.Q = Q;
    res.R = R;
    return res;
}
