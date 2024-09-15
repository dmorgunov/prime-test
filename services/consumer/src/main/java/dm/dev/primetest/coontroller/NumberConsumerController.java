package dm.dev.primetest.coontroller;

import com.opencsv.CSVWriter;
import dm.dev.primetest.service.NumberConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.StringWriter;
import java.util.List;

@RestController
@RequestMapping("/consumer")
@RequiredArgsConstructor
public class NumberConsumerController {

    public final NumberConsumerService numberConsumerService;

    @GetMapping("/download/csv")
    @SneakyThrows
    public ResponseEntity<?> downloadPrimeNums() {
        List<Integer> generatedInts = numberConsumerService.getPrimeList();

        StringWriter stringWriter = new StringWriter();
        try (CSVWriter csvWriter = new CSVWriter(stringWriter)) {
            csvWriter.writeNext(new String[]{"Prime ints"});
            for (Integer i : generatedInts) {
                csvWriter.writeNext(new String[]{String.valueOf(i)});
            }
        }

        byte[] csvBytes = stringWriter.toString().getBytes();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=prime_nums.csv");
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }
}
