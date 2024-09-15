/**
 * TableFilter to filter for entries whose two columns match.
 *
 * @author Matthew Owen
 */
public class ColumnMatchFilter extends TableFilter {
    private String col1;
    private String col2;
    public ColumnMatchFilter(Table input, String colName1, String colName2) {
        super(input);
        col1 = colName1;
        col2 = colName2;
    }

    @Override
    protected boolean keep() {
        int a = headerList().indexOf(col1);
        int b = headerList().indexOf(col2);
        String c = _next.getValue(a);
        String d = _next.getValue(b);
        return c.equals(d);
    }
}
