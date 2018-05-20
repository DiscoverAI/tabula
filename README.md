# Tabula
_Scala_ app to convert [rasa](http://rasa.com/) .md intent files into csv files for classification.

The parsed .md file with all intents will result in two files. One datamart.csv file with all
sentences and their labels and another labels.csv file with all labels and their indices.

## Testing
```bash
./gradlew test
```

## Compiling & Usage
Run
```bash
./gradlew distZip
```
to compile the code. This will create a zip file in `./build/distributions` called `tabula-<VERSION>.zip`.
Unpack that zip file and switch to the newly unpacked folder. Inside that folder execute the compiled
binary with:

```bash
./bin/tabula ${INTENT.MD FILE} ${DESTINATION FOLDER}
```
where **${INTENT.MD FILE}** is the .md file you want to convert and **${DESTINATION FOLDER}** is the
destination folder for all converted files.

This will create a _datamart.csv_, _headers.csv_ and _labels.csv_ file.

The _datamart.csv_ file contains the features and the indexed labels. The _labels.csv_ contains
the index and the labels for each index. The _headers.csv_ contains the name of all the columns
in the _datamart.csv_ file.
