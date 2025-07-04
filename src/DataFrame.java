import java.util.*;

import exceptions.InvalidShape;

public class DataFrame {

    private List<Column> columns;
    private List<Row> rows;
    private int nRows;

    // Constructor por defecto
    public DataFrame() {
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();
        this.nRows = 0;
    }

    // Constructor desde matriz 2D
    public DataFrame(Object[][] array2D, List<Object> labels, List<Object> rowLabels) throws InvalidShape {
        this();
        List<List<Object>> data = new ArrayList<>();
        for (Object[] row : array2D) {
            data.add(Arrays.asList(row));
        }
        generarDataFrame(data, labels, rowLabels);
    }

    // Constructor desde lista de listas
    public DataFrame(List<List<Object>> data, List<Object> labels, List<Object> rowLabels) throws InvalidShape {
        this();
        generarDataFrame(data, labels, rowLabels);
    }

    // Constructor desde una sola columna
    public DataFrame(List<Object> columnData, Object label, List<Object> rowLabels) throws InvalidShape {
        this();
        List<List<Object>> data = new ArrayList<>();
        for (Object value : columnData) {
            List<Object> row = new ArrayList<>();
            row.add(value);
            data.add(row);
        }
        generarDataFrame(data, List.of(label), rowLabels);
    }

    // Constructor desde un mapa de columnas
    public DataFrame(Map<String, List<Object>> columnMap, List<Object> rowLabels) throws InvalidShape {
        this();
        int rowCount = columnMap.values().iterator().next().size(); // asume columnas uniformes
        List<Object> labels = new ArrayList<>(columnMap.keySet());
        List<List<Object>> data = new ArrayList<>();

        for (int i = 0; i < rowCount; i++) {
            List<Object> row = new ArrayList<>();
            for (Object label : labels) {
                row.add(columnMap.get(label).get(i));
            }
            data.add(row);
        }
        generarDataFrame(data, labels, rowLabels);
    }

    // Constructor copia
    public DataFrame(DataFrame other) {
        // Implementación: copiar columnas, celdas y filas según la lógica deseada (profunda o superficial)
    }

    // Método interno para poblar el dataframe
    private void generarDataFrame(List<List<Object>> rows, List<Object> columnLabels, List<Object> rowLabels) throws InvalidShape {

        /*
        Requerimientos pendientes:
        -Los tipos de datos soportados para una columna son: numérico, booleano, cadena.
        -Debe existir un valor especial que haga referencia a valores faltantes (N/A).
        -Una etiqueta (label) puede ser en formato numérico entero o cadena.
        -Dos columnas no pueden tener el mismo nombre. En caso contrario, tirar una excepción.
        -El dataFrame puede generarse a partir de un CSV: La idea es que el constructor convierta un .csv en
         una lista de listas que se pueda pasar como argumento a la función generateDataFrame.

        Cosas a corregir:
        -Modificar validateRows: determinar si es mejor que todas las rows sean del mismo tamaño
        o permitir que sean de tamaños distintos y que los datos que no tienen las filas
        más cortas sean completados por N/A
        - Pasar a columnLabels el numero de columnas como atributo
        */


        validateRows(rows);
        //Manejar labels de columnas: generar nuevos si no label=null, o validar que las labels sean consistentes con la data
        if(columnLabels == null){
            columnLabels = generateColumnLabels(rows);
        }
        //Manejar labels de filas: generar nuevos si no label=null, o validar que las labels sean consistentes con la data
        if(rowLabels == null){
            rowLabels = generateRowLabels(rows);
        }

        fillColumns(rows, columnLabels);
        fillRows(rowLabels);
    }

    private void validateRows(List<List<Object>> rows) throws InvalidShape{
        int ref =0;
        for (int i=0;i<rows.size();i++ ){
            if(i==0){
                ref = rows.get(i).size();
            }
            if (rows.get(i).size()!=ref){
                throw new InvalidShape();
            }
        }
    }


    private List<Object> generateColumnLabels(List<List<Object>> rows){
        int nCols = rows.get(0).size();
        List<Object> labels = new ArrayList<>();
        for(int i=0; i<nCols; i++){
            labels.add(i);
        }
        return labels;
    }

    private List<Object> generateRowLabels(List<List<Object>> rows){
        int nRows = rows.size();
        List<Object> labels = new ArrayList<>();
        for(int i=0; i<nRows; i++){
            labels.add(i);
        }
        return labels;
    }

    private void fillColumns(List<List<Object>> rows, List<Object> columnLabels){
        //Se crea una columna para cada índice de las filas
        for (int i = 0; i < columnLabels.size(); i++) {
            Column column = new Column(columnLabels.get(i));
            for (List<Object> row : rows) {
                column.addCell(new Cell(row.get(i)));
            }
            columns.add(column);
        }
    }

    private void fillRows(List<Object> rowLabels){
        //Se recorre cada columna para generar las filas, esta vez las filas son listas que guardan la referencia a las celdas

        for (int i = 0; i < rowLabels.size(); i++) {
            List<Cell> cellList = new ArrayList<>();
            for (Column column : columns) {
                cellList.add(column.getCell(i));
            }
            this.rows.add(new Row(rowLabels.get(i), cellList));
        }
    }

    public void head(){
        for (Row row : rows){
            System.out.println(row);
        }
    }

    // Cantidad de Columnas
    public int countColumns() {
        return columns.size();
    }

    // Cantidad de Filas
    public int countRows() {
        return rows.size();
    }

    // Columna de tal etiqueta
    public Column getColumnByName(Object name) {
        for (Column column : columns) {
            if (Objects.equals(column.getLabel(), name)) {
                return column;
            }
        }
        return null; // o lanzar una excepción
    }

    // Fila de tal etiqueta
    public Row getRowByLabel(Object label) {
        for (Row row : rows) {
            if (Objects.equals(row.getLabel(), label)) {
                return row;
            }
        }
        return null;
    }
    //asfda
}
