package ra.academy.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handlerValidateSignUpForm(MethodArgumentNotValidException e) {
        // xử lí lỗi
        Map<String, String> map = new HashMap<>();
        if (!e.getBindingResult().hasFieldErrors()){
            return new ResponseEntity<>(e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        e.getBindingResult().getFieldErrors().forEach(err ->
                map.put(err.getField(), err.getDefaultMessage()));

        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }
}
