package br.mack.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Trends implements Serializable {
    private String term;
    private List<Result> results;

    public Trends() {
        this.results = new ArrayList();
    }

    public Trends(String term, List<Result> data) {
        this.term = term;
        this.results = data;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results == null ? new ArrayList() : results;
    }
}
