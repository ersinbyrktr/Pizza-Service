package rest.v1.pojos;

public class Error {
    private String swagger_location="/swagger-ui.html";

    // for Jackson
    protected Error() {
    }

    public Error(String base_url){
        swagger_location = base_url + swagger_location;
    }

    public Integer getStatus_code() {
        return 404;
    }

    public String getMessage() {
        return "URL NOT FOUND";
    }

    public String getSwagger_location() {
        return swagger_location;
    }
}
