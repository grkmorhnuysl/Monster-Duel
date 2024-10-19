public class STRINT {
    String name;
    int value;

    STRINT(String name) {
        this.name = name;
        this.value = 0;
    }

    STRINT(int value) {
        this.value = value;
        this.name = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        STRINT strint = (STRINT) o;
        if (this.name != null && strint.name != null) {
            return this.name.equals(strint.name);
        }
        if (this.name == null && strint.name == null) {
            return this.value == strint.value;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (name != null) ? name.hashCode() : Integer.hashCode(value);
    }

    @Override
    public String toString() {
        return (name != null) ? name : String.valueOf(value);
    }
}
