package br.com.dio.model;

public class Space {
    private Integer actual;
    private int expected;
    private boolean fixed;

    public Space(int expected, boolean fixed){
        this.expected = expected;
        this.fixed = fixed;
        if(fixed){
            actual = expected;
        }
    }

    public Integer getActual() {
        return actual;
    }

    public void setActual(Integer actual) {
        if(fixed) return;
        this.actual = actual;
    }

    public void setExpected(int expected) {
        this.expected = expected;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public void clearSpace(){
        setActual(null);
    }

    public int getExpected() {
        return expected;
    }

    public boolean isFixed() {
        return fixed;
    }

}
