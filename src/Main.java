import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<List<Object>> data = new ArrayList<>();
        data.add(List.of(1, 2, 3));
        data.add(List.of(4, 5, 6));
        data.add(List.of(7, 8, 9));

        List<Object> labels = List.of("A", "B", "C");

        DataFrame df = new DataFrame(data, labels);
        System.out.println("Valor en fila 1, columna 2: " +
                df.getRow(1).getCell(2).getContent()); // Imprime 6
    }
}
