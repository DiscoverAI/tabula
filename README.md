# Tabula
_Scala_ app to convert [rasa](http://rasa.com/) .md intent files into csv files for classification.

The parsed .md file with all intents will result in two files. One datamart.csv file with all
sentences and their labels and another labels.csv file with all labels and their indices.

## Data
### Input
Uses the [rasa markdown format](https://rasa.com/docs/nlu/dataformat/):
```
...
## intent:greet
- hey
- hello
...
```

### Output
_train.csv_, _test.csv_, _headers.csv_ and _labels.csv_ files.
The _train.csv_ and _test.csv_ files contain the features with the indexed labels (first column).
The _labels.csv_ contains the index and the labels for each index. The _headers.csv_ contains the name
of all the columns in the _train.csv_ and _test.csv_ files.

train/test example:
```
...
3,"no sorry"
...
```

labels example:
```
...
8,random
...
```

headers example:
```
label,sentence
```

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

This will create a _train.csv_, _test.csv_, _headers.csv_ and _labels.csv_ file.

The _train.csv_ and _test.csv_ files contain the features with the indexed labels (first column).
The _labels.csv_ contains the index and the labels for each index. The _headers.csv_ contains the name
of all the columns in the _train.csv_ and _test.csv_ files.
