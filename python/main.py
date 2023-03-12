from intergal import *

# integral(function, segments, n, mode)
#
# function - lambda function
# segments - segments of taking integral(note2)
# n - number of segments
# mode - selection method (left, right, center, random)
#
# returns a value

# printIntegralSum(function, segments, n, mode, pic=False, format="png", nameFile="picture", path="./")
#
# required arguments are similar to integral
#
# pic - create file
# nameFile - file name
# format - file format
# path - path to file ({eps, jpg, jpeg, pdf, pgf, png, ps, raw, rgba, svg, svgz, tif, tiff, webp)

function = lambda x: x ** 3 + 1
segments = [0, 2]
n = 50
mode = "left"

print(integral(function, segments, n, mode))
printIntegralSum(function, segments, n, mode, pic=True, nameFile="котенок", format="eps", path="./мурмур/")

# for i in range(3):
#     printIntegralSum(function, [0, 2], 5 + 10 * i, "random", pic=True, nameFile="Randpic" + str(i))

# note1:
#
# e^a = exp(a)
# a^b = pow(a, b)
# a! = factorial(a)
# a mod b = fmod(a, b)
#
# ln(a) = log(a)
# log_b(a) = log(a, b)
#
# sqrt(a) = sqrt(a)
#
# sin(a) = sin(a)
# cos(a) = cos(a)
# tg(a) = tan(a)
# arccos(a) = acos(a)
# arcsin(a) = asin(a)
# arctg(a) = atan(a)
#
# rad -> deg: degrees(a)
# deg -> rad: radians(a)
#
# cosh, sinh, tanh, acosh, asinh, atanh
#
# const: pi, e
#
# |a| = fabs(a)
#
# ⎡a⎤ = ceil(a)
# ⌊a⌋ = floor(a)
# [a] = floor(a + 0.5)

# note2:
#
# segment: [a, b]
# segments: [[a, b], [c, d]] <==> f(func, [a, b], n1, mode) + f(func, [c, d], n2, mode)
# n1 = ⎡((b - a) * n / (b - a + d - c)⎤, n2 = ⎡((d - c) * n / (b - a + d - c)⎤
