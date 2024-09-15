/**
 * TableFilter to filter for entries greater than a given string.
 *
 * @author Matthew Owen
 */
public class GreaterThanFilter extends TableFilter {
    private String colN;
    private String reference;
    public GreaterThanFilter(Table input, String colName, String ref) {
        super(input);
        colN = colName;
        reference = ref;
    }

    @Override
    protected boolean keep() {
        String a = candidateNext().getValue(headerList().indexOf(colN));
        return a.compareTo(reference)>0;

    }

}
