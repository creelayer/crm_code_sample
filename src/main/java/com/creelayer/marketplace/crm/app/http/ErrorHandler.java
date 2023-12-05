package com.creelayer.marketplace.crm.app.http;

import com.creelayer.marketplace.crm.common.ForbiddenException;
import com.creelayer.marketplace.crm.common.NotFoundException;
import com.creelayer.marketplace.crm.order.core.exception.CheckoutException;
import com.creelayer.marketplace.crm.secutiry.DomainPermissionForbiddenException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {

        if (converterType.equals(StringHttpMessageConverter.class))
            return false;

        if (returnType.getMethod() != null && returnType.getMethod().getName().equals("error"))
            return false;

        return true;
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response
    ) {

        if (body instanceof Page) {
            Page<?> page = (Page<?>) body;
            response.getHeaders().add("X-Page-Count", String.valueOf(page.getTotalPages()));
            response.getHeaders().add("X-Current-Page", String.valueOf(page.getNumber()));
            response.getHeaders().add("X-Page-Size", String.valueOf(page.getSize()));
            response.getHeaders().add("X-Total", String.valueOf(page.getTotalElements()));
            return new RestResponse(page.getContent());
        }

        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();

        RestResponse restResponse = new RestResponse(body);
        restResponse.status = HttpStatus.valueOf(servletResponse.getStatus());

        return restResponse;
    }

    @ExceptionHandler(value = {DomainPermissionForbiddenException.class})
    protected ProblemDetail handleConflict(DomainPermissionForbiddenException ex) {

        HttpServletResponse httpServletResponse = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();

        httpServletResponse.setHeader(
                "WWW-Authenticate", "UMA realm=\"cree\", " +
                        "as_uri=\"https://auth.creelayer.com/realms/cree\", " +
                        "resource=\"" + ex.getIdentity().getId() + "#" + ex.getPermission() + "\"");

        return ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(value = {ForbiddenException.class,})
    protected ProblemDetail handleForbidden(Exception ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(value = {
            NotFoundException.class,
            MissingPathVariableException.class,
            NoSuchElementException.class,
            EntityNotFoundException.class
    })
    protected ProblemDetail handleNotFound(Exception ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(value = {
            CheckoutException.class,
            ConstraintViolationException.class,
            ValidationException.class,
            EntityExistsException.class
    })
    protected ProblemDetail handleConflict(RuntimeException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> onConstraintValidationException(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(e.getBindingResult().getFieldErrors().stream()
                .map(v -> new Violation(v.getField(), v.getDefaultMessage()))
                .collect(Collectors.toList()));
    }

    private static class RestResponse implements Serializable {

        public HttpStatus status;
        public int code;

        public Object content;

        public RestResponse(Object content) {
            this.status = HttpStatus.OK;
            this.content = content;
        }
    }

    public static class Violation {
        public String field;
        public String message;

        public Violation(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }
}
