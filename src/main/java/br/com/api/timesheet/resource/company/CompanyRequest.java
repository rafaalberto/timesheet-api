package br.com.api.timesheet.resource.company;

import lombok.Builder;

import java.util.Optional;

@Builder
public class CompanyRequest {

    private Integer page;
    private Integer size;
    private String document;
    private String name;

    public Optional<Integer> getPage() {
        return Optional.ofNullable(page);
    }

    public Optional<Integer> getSize() {
        return Optional.ofNullable(size);
    }

    public Optional<String> getDocument() {
        return Optional.ofNullable(document);
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

}
