package br.edu.app;
import java.util.Map;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;
@RestControllerAdvice
public class ApiErrors {
    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<?> conflito(DataIntegrityViolationException e) {return ResponseEntity.status(409).body(Map.of("erro","Registro duplicado ou restrição de integridade."));}
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<?> invalido(MethodArgumentNotValidException e) {return ResponseEntity.badRequest().body(Map.of("erro",e.getBindingResult().getFieldErrors().stream().map(f->f.getField()+": "+f.getDefaultMessage()).toList()));}
}
