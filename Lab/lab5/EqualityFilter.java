/**
 * TableFilter to filter for entries equal to a given string.
 *
 * @author Matthew Owen
 */
public class EqualityFilter extends TableFilter {
    private String colN;
    private String mat;
    public EqualityFilter(Table input, String colName, String match) {
        super(input);
        colN = colName;
        mat = match;
    }

    @Override
    protected boolean keep() {
        String a = candidateNext().getValue(headerList().indexOf(colN));
        return mat.equals(a);
    }
}
