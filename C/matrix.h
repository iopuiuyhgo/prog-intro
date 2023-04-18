#include <complex.h>
#include <math.h>
#include <stdio.h>
#include <stdlib.h>
typedef struct
{
	int x;
	int y;
	double *array;
} doubleMatrix;

void printArray(doubleMatrix a)
{
	for (int i = 0; i < a.y; ++i)
	{
		printf("");
		for (int j = 0; j < a.x; ++j)
		{
			printf("%15f", a.array[i * a.x + j]);
			if (j < a.x - 1)
			{
				printf(" ");
			}
		}
		printf("\n");
	}
}
doubleMatrix initUndefined(int x, int y)
{
	doubleMatrix res;
	res.x = x;
	res.y = y;
	res.array = malloc(sizeof(double) * x * y);
	return res;
}
doubleMatrix initZero(int x, int y)
{
	doubleMatrix res;
	res.x = x;
	res.y = y;
	res.array = malloc(sizeof(double) * x * y);
	if (!res.array)
	{
		return res;
	}
	for (int i = 0; i < res.x * res.y; ++i)
	{
		res.array[i] = 0;
	}
	return res;
}
void cutMatrix(doubleMatrix a, int startX, int startY, int endX, int endY, doubleMatrix res)
{
	for (int i = startY; i < endY; ++i)
	{
		for (int j = startX; j < endX; ++j)
		{
			res.array[(i - startY) * res.x + (j - startX)] = a.array[i * a.x + j];
		}
	}
}
double vectorLength(doubleMatrix vector)
{
	double res = 0;
	for (int i = 0; i < vector.x * vector.y; ++i)
	{
		res += vector.array[i] * vector.array[i];
	}
	return sqrt(res);
}
void subtractMatrix(doubleMatrix a, doubleMatrix b, doubleMatrix res)
{
	for (int i = 0; i < a.x * a.y; ++i)
	{
		res.array[i] = a.array[i] - b.array[i];
	}
}
void addMatrix(doubleMatrix a, doubleMatrix b, doubleMatrix res)
{
	for (int i = 0; i < a.x * a.y; ++i)
	{
		res.array[i] = a.array[i] + b.array[i];
	}
}
void scalarMultiply(doubleMatrix a, double b, doubleMatrix res)
{
	for (int i = 0; i < a.x * a.y; ++i)
	{
		res.array[i] = a.array[i] * b;
	}
}
double get(doubleMatrix m, int i, int j)
{
	return m.array[i * m.x + j];
}
void set(doubleMatrix m, int i, int j, double e)
{
	m.array[i * m.x + j] = e;
}
doubleMatrix initOne(int x, int y)
{
	doubleMatrix res;
	res.x = x;
	res.y = y;
	res.array = malloc(sizeof(double) * x * y);
	if (!res.array)
	{
		return res;
	}
	for (int i = 0; i < res.x * res.y; ++i)
	{
		res.array[i] = 1;
	}
	return res;
}
doubleMatrix initArray(double *a, int x, int y)
{
	doubleMatrix res;
	res.array = a;
	res.x = x;
	res.y = y;
	return res;
}
doubleMatrix copyArray(const double *a, int x, int y)
{
	doubleMatrix res = initUndefined(x, y);
	if (!res.array)
	{
		return res;
	}
	for (int i = 0; i < x * y; ++i)
	{
		res.array[i] = a[i];
	}
	return res;
}
doubleMatrix initEye(int l)
{
	doubleMatrix res;
	res.x = l;
	res.y = l;
	res.array = malloc(sizeof(double) * l * l);
	if (!res.array)
	{
		return res;
	}
	for (int i = 0; i < l; i++)
	{
		for (int j = 0; j < l; j++)
		{
			if (i == j)
			{
				res.array[i * l + j] = 1;
			}
			else
			{
				res.array[i * l + j] = 0;
			}
		}
	}
	return res;
}

int transpose(doubleMatrix a, doubleMatrix res)
{
	for (int i = 0; i < a.y; ++i)
	{
		for (int j = 0; j < a.x; ++j)
		{
			res.array[j * a.y + i] = a.array[i * a.x + j];
		}
	}
	return 0;
}
doubleMatrix dot(doubleMatrix a, doubleMatrix b)
{
	doubleMatrix res = initZero(b.x, a.y);
	if (!res.array)
	{
		return res;
	}
	for (int i = 0; i < res.y; ++i)
	{
		for (int j = 0; j < res.x; ++j)
		{
			for (int k = 0; k < a.x; ++k)
			{
				res.array[i * res.x + j] += a.array[i * a.x + k] * b.array[k * b.x + j];
			}
		}
	}
	return res;
}

int hessenbergRotateLine(doubleMatrix matrixRQ, doubleMatrix matrixR, int line)
{
	double cos;
	double sin;
	if (sqrt(pow(matrixR.array[line * matrixR.x + line], 2) + pow(matrixR.array[(line + 1) * matrixR.x + line], 2)))
	{
		cos = matrixR.array[line * matrixR.x + line] /
			  sqrt(pow(matrixR.array[line * matrixR.x + line], 2) + pow(matrixR.array[(line + 1) * matrixR.x + line], 2));
		sin = matrixR.array[(line + 1) * matrixR.x + line] /
			  sqrt(pow(matrixR.array[line * matrixR.x + line], 2) + pow(matrixR.array[(line + 1) * matrixR.x + line], 2));
	}
	else
	{
		cos = 1;
		sin = 0;
	}
	for (int i = 0; i < matrixR.x; ++i)
	{
		double tmp = matrixR.array[line * matrixR.x + i];
		matrixR.array[line * matrixR.x + i] =
			matrixR.array[line * matrixR.x + i] * cos + matrixR.array[(line + 1) * matrixR.x + i] * sin;
		matrixR.array[(line + 1) * matrixR.x + i] = matrixR.array[(line + 1) * matrixR.x + i] * cos - tmp * sin;

		tmp = matrixRQ.array[line * matrixRQ.x + i];
		matrixRQ.array[line * matrixRQ.x + i] =
			matrixRQ.array[line * matrixRQ.x + i] * cos + matrixRQ.array[(line + 1) * matrixRQ.x + i] * sin;
		matrixRQ.array[(line + 1) * matrixRQ.x + i] = matrixRQ.array[(line + 1) * matrixRQ.x + i] * cos - tmp * sin;
	}

	for (int i = 0; i < matrixRQ.y; ++i)
	{
		double tmp = matrixRQ.array[i * matrixRQ.x + line];
		matrixRQ.array[i * matrixRQ.x + line] =
			matrixRQ.array[i * matrixRQ.x + line] * cos + matrixRQ.array[i * matrixRQ.x + (line + 1)] * sin;
		matrixRQ.array[i * matrixRQ.x + (line + 1)] = matrixRQ.array[i * matrixRQ.x + (line + 1)] * cos - tmp * sin;
	}
	return 0;
}
double underDiagSum(doubleMatrix matrix, double eps)
{
	double res = 0;
	for (int i = 0; i < matrix.x - 1; ++i)
	{
		if (fabs(matrix.array[(i + 1) * matrix.x + i]) < eps)
		{
			res += fabs(matrix.array[(i + 1) * matrix.x + i]);
		}
	}
	return res;
}
int cutCross(doubleMatrix *matrix, int startX, int startY, int endX, int endY)
{
	doubleMatrix newMatrix = initUndefined(matrix->x + startX - endX - 1, matrix->y + startY - endY - 1);
	if (!newMatrix.array)
	{
		return 2;
	}
	int count = 0;
	for (int i = 0; i < matrix->y; ++i)
	{
		for (int j = 0; j < matrix->x; ++j)
		{
			if ((i < startY || i > endY) && (j < startX || j > endX))
			{
				newMatrix.array[count] = matrix->array[i * matrix->x + j];
				count+=1;
			}
		}
	}
	free(matrix->array);
	*matrix = newMatrix;
	return 0;
}
double avg(doubleMatrix matrix)
{
	double res = 0;
	for (int i = 0; i < matrix.x * matrix.y; ++i)
	{
		res += fabs(matrix.array[i]);
	}
	res /= (matrix.x * matrix.y);
	return res;
}
