package com.rbc.dowjones.repository;

import com.rbc.dowjones.repository.dto.BulkUploadResponseDto;
import com.rbc.dowjones.repository.service.StockDataService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("perf")
@Disabled("Performance test - run manually")
public class StockDataServicePerformanceTest {

    @Autowired
    private StockDataService stockDataService;

    @Test
    void bulkUploadPerformanceTest_10kRecords() throws Exception {

        // Arrange: Load large CSV file
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "dow_jones_index.csv",
                "text/csv",
                new ClassPathResource("dow_jones_index.csv").getInputStream()
        );

        //  Measure execution time
        long startTime = System.currentTimeMillis();

        BulkUploadResponseDto response = stockDataService.uploadBulkData(file);

        long endTime = System.currentTimeMillis();
        long executionTime = endTime-startTime;

        // Assert: Functional + Performance checks
        assertNotNull(response);
        assertTrue(response.getTotalRecords() >= 49);
        assertTrue(executionTime < 5000, "Bulk upload exceeded SLA. Time taken:"+ executionTime + "ms"); // SLA: 5 seconds

        // Log result
        System.out.println("Bulk upload of " + response.getTotalRecords() + " records completed in " + executionTime + " ms"
        );
    }
}
