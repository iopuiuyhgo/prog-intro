from math import *
import random
import os
import matplotlib.pyplot as plt
from matplotlib.patches import Rectangle
import numpy as np

modes = {
    "left": lambda x, y: x,
    "right": lambda x, y: y,
    "center": lambda x, y: (x + y) / 2,
    "random": lambda x, y: random.uniform(x, y),
}


def printIntegralSum(function, segments, n, mode, pic=False, format="png", nameFile="picture", path="./"):
    def save(name, fmt, path):
        pwd = os.getcwd()
        iPath = path.format(fmt)
        if not os.path.exists(iPath):
            os.mkdir(iPath)
        os.chdir(iPath)
        plt.savefig('{}.{}'.format(name, fmt))
        os.chdir(pwd)
        plt.close()

    def printRects(start, end, acc):
        acc = int(acc)
        for i in range(acc):
            i = (end - start) * i / acc

            col = [255 / 255, 69 / 255, 0 / 255]
            mod = function(modes.get(mode)(start + i, start + i + (end - start) / acc))
            # ax.add_patch(Rectangle((start + i, 0), (end - start) / acc, function(modes.get(mode)(start + i,
            #       start + i + (end - start) / acc)), color=col))
            ax.add_patch(Rectangle((start + i, 0), 0, mod, color=col))
            ax.add_patch(Rectangle((start + i + (end - start) / acc, 0), 0, mod, color=col))
            ax.add_patch(Rectangle((start + i, mod), (end - start) / acc, 0, color=col))

    fig, ax = plt.subplots()

    s = 0
    if isinstance(segments[0], list):
        start = segments[0][0]
        end = segments[len(segments) - 1][1]
        for i in range(len(segments)):
            s += segments[i][1] - segments[i][0]

        for i in range(len(segments)):
            printRects(segments[i][0], segments[i][1], n * (segments[i][1] - segments[i][0]) / s)
    else:
        start = segments[0]
        end = segments[1]
        s = end - start
        printRects(start, end, n * (end - start) / s)

    acc = (end - start) / 40

    x = np.arange(start, end + acc, acc)
    y = list(map(function, x))
    ax.plot(x, y)
    if pic:
        save(nameFile, format, path)
    plt.show()


def integral(function, segments, n, mode):
    result = 0
    if isinstance(segments[0], list):
        s = 0
        for i in range(len(segments)):
            s += segments[i][1] - segments[i][0]
        for i in range(len(segments)):
            result += integral(function, segments[i], ceil(n * (segments[i][1] - segments[i][0]) / s), mode)
        return result

    def getX(i):
        return segments[0] + (segments[1] - segments[0]) * i / n

    for i in range(n):
        x = modes.get(mode)(getX(i), getX(i + 1))
        result += (segments[1] - segments[0]) * function(x) / n
    return result
