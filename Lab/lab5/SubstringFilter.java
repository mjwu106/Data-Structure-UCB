/**
 * TableFilter to filter for containing substrings.
 *
 * @author Matthew Owen
 */
public class SubstringFilter extends TableFilter {
    private String colN;
    private String sub;
    public SubstringFilter(Table input, String colName, String subStr) {
        super(input);
        colN = colName;
        sub = subStr;
    }

    @Override
    protected boolean keep() {
        String a = candidateNext().getValue(headerList().indexOf(colN));
        return a.contains(sub);
    }

}
