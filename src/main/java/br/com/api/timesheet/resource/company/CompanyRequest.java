package br.com.api.timesheet.resource.company;

import java.util.Optional;

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

    public static final class Builder {
        private Integer page;
        private Integer size;
        private String document;
        private String name;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder withPage(Integer page) {
            this.page = page;
            return this;
        }

        public Builder withSize(Integer size) {
            this.size = size;
            return this;
        }

        public Builder withDocument(String document) {
            this.document = document;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public CompanyRequest build() {
            CompanyRequest companyRequest = new CompanyRequest();
            companyRequest.page = this.page;
            companyRequest.size = this.size;
            companyRequest.document = this.document;
            companyRequest.name = this.name;
            return companyRequest;
        }
    }
}
