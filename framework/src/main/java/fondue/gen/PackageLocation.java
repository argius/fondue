package fondue.gen;

public final class PackageLocation {

    private final String pkg;

    public PackageLocation(String pkg) {
        this.pkg = pkg;
    }

    public String toPath() {
        return pkg.replace(".", "/");
    }

    public PackageLocation append(String s) {
        return new PackageLocation(String.format("%s.%s", pkg, s));
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", getClass().getSimpleName(), pkg);
    }
}
