package fondue.fw;

import org.apache.commons.lang3.Range;

/**
 * PP: Page Parameter.
 */
public final class PP {

    private static final int DEFAULT_COUNT_PER_PAGE = 20;

    private int countPerPage;
    private boolean disabled;
    private int currentPage;
    private long totalCount;
    private String query;

    public PP() {
        this.countPerPage = DEFAULT_COUNT_PER_PAGE;
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
        assert totalCount >= 0L;
        if (totalCount == 0) {
            return 1;
        }
        long c = Math.floorDiv(totalCount, countPerPage) + (Math.floorMod(totalCount, countPerPage) > 0 ? 1 : 0);
        assert c > 0L && c <= Integer.MAX_VALUE;
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

    public int getCountPerPage() {
        return countPerPage;
    }

    public void setCountPerPage(int countPerPage) {
        if (countPerPage < 1) {
            throw new IllegalArgumentException("countPerPage = " + countPerPage);
        }
        this.countPerPage = countPerPage;
    }

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
        if (totalCount < 0) {
            throw new IllegalArgumentException("totalCount = " + totalCount);
        }
        this.totalCount = totalCount;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + countPerPage;
        result = prime * result + currentPage;
        result = prime * result + (disabled ? 1231 : 1237);
        result = prime * result + ((query == null) ? 0 : query.hashCode());
        result = prime * result + (int) (totalCount ^ (totalCount >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof PP)) {
            return false;
        }
        PP other = (PP) obj;
        if (countPerPage != other.countPerPage) {
            return false;
        }
        if (currentPage != other.currentPage) {
            return false;
        }
        if (disabled != other.disabled) {
            return false;
        }
        if (query == null) {
            if (other.query != null) {
                return false;
            }
        } else if (!query.equals(other.query)) {
            return false;
        }
        if (totalCount != other.totalCount) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("PP(countPerPage=%s, disabled=%s, currentPage=%s, totalCount=%s, query=%s)",
            countPerPage, disabled, currentPage, totalCount, query);
    }

}
