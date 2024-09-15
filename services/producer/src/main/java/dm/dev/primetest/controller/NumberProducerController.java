package dm.dev.primetest.controller;

import com.opencsv.CSVWriter;
import dm.dev.primetest.dto.GeneratedStatusDto;
import dm.dev.primetest.service.NumberService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/producer")
public class NumberProducerController {
    private final NumberService numberService;

    @GetMapping("/status")
    public ResponseEntity<GeneratedStatusDto> getStatus() {
        return ResponseEntity.ok()
                .body(numberService.getStatus());
    }

    @GetMapping("/download/csv")
    @SneakyThrows
    public ResponseEntity<?> getNumberStream() {
        List<Integer> generatedInts = numberService.getGeneratedInts();

        StringWriter stringWriter = new StringWriter();
        try (CSVWriter csvWriter = new CSVWriter(stringWriter)) {
            csvWriter.writeNext(new String[]{"Generated ints"});
            for (Integer i : generatedInts) {
                csvWriter.writeNext(new String[]{String.valueOf(i)});
            }
        }

        byte[] csvBytes = stringWriter.toString().getBytes();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=produced.csv");
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }


}