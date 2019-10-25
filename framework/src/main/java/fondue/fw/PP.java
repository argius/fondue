package fondue.fw;

import org.apache.commons.lang3.Range;

/**
 * PP: Page Parameter.
 */
public final class PP {

    private static final int countPerPage = 20;

    private boolean disabled;
    private int currentPage;
    private long totalCount;
    private String query;

    public PP() {
        this.query = "";
    }

    public void validateCurrentPage() {
        if (!isValidCurrentPage()) {
            currentPage = 1;
        }
    }

    public boolean isValidCurrentPage() {
        if (!disabled) {
            Range<Integer> range = Range.between(1, getLastPage());
            return range.contains(currentPage);
        }
        return false;
    }

    public boolean isFirst() {
        return currentPage == 1;
    }

    public boolean isLast() {
        return currentPage == getLastPage();
    }

    public int getLastPage() {
        if (totalCount == 0) {
            return 1;
        }
        long c = Math.floorDiv(totalCount, countPerPage) + (Math.floorMod(totalCount, countPerPage) > 0 ? 1 : 0);
        if (c > Integer.MAX_VALUE) {
            throw new UnsupportedOperationException("page count too large (some kind of mistake?): count = " + c);
        }
        if (c == 0) {
            throw new IllegalStateException("page: " + this);
        }
        return (int) c;
    }

    public int offset() {
        if (isValidCurrentPage()) {
            return (currentPage - 1) * countPerPage;
        }
        return 0;
    }

    public int limit() {
        return countPerPage;
    }

    // getter-setter

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public String toString() {
        return "PP(disabled=" + disabled + ",currentPage=" + currentPage + ",totalCount=" + totalCount + ")";
    }

}
