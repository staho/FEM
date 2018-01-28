import csv

with open ("../test1.csv", newline="") as csvFile:
    with open("../nodesData.csv", 'w') as csvSave:
        femReader = csv.reader(csvFile, delimiter=",")
        femWriter = csv.writer(csvSave, delimiter=",")

        i = 0
        for row in femReader:
            if i % 61 == 0:
                tempRow = row[0].split(", ")
                femWriter.writerow([tempRow[0], tempRow[5], tempRow[22], tempRow[59]])
                print(tempRow[0], " ", tempRow[5], " ", tempRow[22], " ", tempRow[59])
            i += 1
