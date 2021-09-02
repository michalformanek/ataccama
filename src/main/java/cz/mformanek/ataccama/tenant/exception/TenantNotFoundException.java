package cz.mformanek.ataccama.tenant.exception;

public class TenantNotFoundException extends RuntimeException {
    public TenantNotFoundException(String message) {
        super(message);
    }
}
