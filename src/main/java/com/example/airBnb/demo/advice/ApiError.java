//package com.example.airBnb.demo.advice;
//
//import lombok.Builder;
//import lombok.Data;
//import org.springframework.http.HttpStatus;
//
//import java.util.List;
//
//@Data
//@Builder
//public class ApiError {
//    public HttpStatus getStatus() {
//        return status;
//    }
//
//
//    public void setStatus(HttpStatus status) {
//        this.status = status;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public List<String> getSubErrors() {
//        return subErrors;
//    }
//
//    public void setSubErrors(List<String> subErrors) {
//        this.subErrors = subErrors;
//    }
//
//    private HttpStatus status;
//    private String message;
//    private List<String> subErrors;
//}
package com.example.airBnb.demo.advice;

import org.springframework.http.HttpStatus;
import java.util.List;

public class ApiError {
    private HttpStatus status;
    private String message;
    private List<String> subErrors;

    // Private constructor for Builder
    private ApiError(Builder builder) {
        this.status = builder.status;
        this.message = builder.message;
        this.subErrors = builder.subErrors;
    }

    // Getters and Setters
    public HttpStatus getStatus() { return status; }
    public void setStatus(HttpStatus status) { this.status = status; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public List<String> getSubErrors() { return subErrors; }
    public void setSubErrors(List<String> subErrors) { this.subErrors = subErrors; }

    // Builder Class
    public static class Builder {
        private HttpStatus status;
        private String message;
        private List<String> subErrors;

        public Builder status(HttpStatus status) {
            this.status = status;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder subErrors(List<String> subErrors) {
            this.subErrors = subErrors;
            return this;
        }

        public ApiError build() {
            return new ApiError(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
