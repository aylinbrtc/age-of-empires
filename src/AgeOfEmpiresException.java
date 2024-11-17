public class AgeOfEmpiresException extends Exception{
    private String message;
    public AgeOfEmpiresException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
