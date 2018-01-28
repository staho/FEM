import matplotlib.pyplot as plt
from mpl_toolkits.axes_grid1 import make_axes_locatable
import numpy as np
import csv
import matplotlib.ticker as ticker

with open ("../test1.csv", newline="") as csvFile:
    dtau = 12

    femReader = csv.reader(csvFile, delimiter=",")
    cycleCounter = 0
    rowCounter = 0
    numbers = []
    numbers.append([])
    for row in femReader:
        tempNumbers = numbers[cycleCounter]
        if row[0] == '\n':
            cycleCounter += 1
            numbers.append([])
            rowCounter = 0
        else:
            tempRow = row[0].split(', ')
            for i in range(len(tempRow)):
                tempRow[i] = float(tempRow[i])

            rowCounter += 1
            tempNumbers.append(tempRow)

    numOfRows = len(numbers) - 1
    numOfChosen = int(numOfRows/8)
    chosen = [i * numOfChosen for i in range(9)]

    fig, axes = plt.subplots(nrows=3, ncols=3)
    numbersIter = 0
    for ax in axes.flat:
        im = ax.imshow(numbers[chosen[numbersIter]])
        ax.set_xlabel("CM")
        ax.set_ylabel("CM")

        ax.xaxis.set_major_locator(ticker.FixedLocator((np.arange(0, 60, 3.75))))
        ax.xaxis.set_major_formatter(ticker.FixedFormatter(([0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16])))
        ax.yaxis.set_major_locator(ticker.FixedLocator((np.arange(0, 60, 3.75))))
        ax.yaxis.set_major_formatter(ticker.FixedFormatter(([0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16])))
        # ax.set_xticks()
        # ax.set_yticks(np.arange(0, 0.1, 0.01))
        ax.set_title("Wand after: " + str(chosen[numbersIter] * dtau + 12) + " seconds")
        numbersIter += 1

        divider = make_axes_locatable(ax)
        cax = divider.append_axes("right", size="5%", pad=0.05)
        fig.colorbar(im, cax=cax)

    plt.show()
