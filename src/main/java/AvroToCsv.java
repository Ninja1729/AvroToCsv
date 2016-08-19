import com.truecar.dataproduct.common.vehicle.DealerVehicleSale;
import com.truecar.di.pricing.common.EnrichedVehicle;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.generic.GenericData;
import org.apache.avro.io.DatumReader;
import org.apache.avro.specific.SpecificDatumReader;
import com.truecar.common.Vehicle;
import com.truecar.di.canonicaldata.avro.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Created by nkandavel on 8/5/16.
 */
public class AvroToCsv {


    public static void main(String[] args) throws IOException {


        List<String> lines = newArrayList();
        Path file = Paths.get("ev.csv");
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        int count = 0;


        URL url = AvroToCsv.class.getResource("vehicle.avro");
        File testAvroFile = new File(url.getFile());

        DatumReader<GenericData.Record> datumReader = new SpecificDatumReader<GenericData.Record>();
        DataFileReader<GenericData.Record> dataFileReader = new DataFileReader<GenericData.Record>(testAvroFile, datumReader);
        GenericData.Record avroObject = null;
        int i = 0;
        while (dataFileReader.hasNext()) {
            avroObject = dataFileReader.next(avroObject);

            if (i == 1) {
                System.out.println(i);
                System.out.println(avroObject.get(0));
                EnrichedVehicle vehicle = (EnrichedVehicle) avroObject.get(1);
                System.out.println(vehicle.getVehicle());
                System.out.println(vehicle.getPricingInfo());
                System.out.println(vehicle.getSpecialFees());
                System.out.println(vehicle.getVehicle().getMake());
                break;
            }
            i++;
        }


        Files.write(file, lines, Charset.forName("UTF-8"));

        System.out.println("total count of items is: " + count);
    }


}