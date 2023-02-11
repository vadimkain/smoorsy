    package com.smoorsy.model.entity.organization_schema;

    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class Department {
        private Long id;
        private String city;
    }
