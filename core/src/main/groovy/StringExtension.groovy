class StringExtension {

    public static String reverseToUpperCase(String self) {
        StringBuilder sb = new StringBuilder(self);
        sb.reverse();
        return sb.toString().toUpperCase();
    }

}
