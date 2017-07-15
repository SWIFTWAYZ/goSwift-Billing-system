package com.swiftwayz.service.driver;

import com.swiftwayz.domain.user.Driver;
import com.swiftwayz.domain.user.DriverDetail;
import com.swiftwayz.domain.user.VehicleOwner;
import com.swiftwayz.domain.vehicle.Vehicle;
import com.swiftwayz.repository.DriverRepository;
import com.swiftwayz.service.driver.adabt.DriverAdaptor;
import com.swiftwayz.service.rest.VehicleRestService;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by sydney on 2017/04/17.
 */
@Service
@Transactional
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private VehicleRestService vehicleService;

    public Driver addDriver(Driver driver) {
        DriverDetail driverDetail = DriverAdaptor.adapt(driver);

        Vehicle vehicle = driverDetail.getVehicle();

        vehicleService.addVehicle(vehicle);

        VehicleOwner vehicleOwner = driverDetail.getVehicleOwner();
        Validate.notNull(vehicleOwner, "Vehicle owner is required.");

        DriverDetail saved = driverRepository.save(driverDetail);
        return DriverAdaptor.adapt(saved);
    }

    public Driver findOne(Long id){
        DriverDetail driverDetail = driverRepository.findOne(id);
        Validate.notNull(driverDetail, "Driver "+ id + " not found");
        return DriverAdaptor.adapt(driverDetail);
    }

    public Driver findByIdNumber(Long idNumber) {
        Optional<DriverDetail> driverD = driverRepository.findByIdNumber(idNumber);
        Validate.isTrue(driverD.isPresent(),  "Driver "+ idNumber +" not found.");
        return DriverAdaptor.adapt(driverD.get());
    }

    public Driver updateDriver(Driver driver){

        DriverDetail driverDetail = DriverAdaptor.adapt(driver);

        Vehicle vehicle = driverDetail.getVehicle();
//        validateVehicle(vehicle);

        VehicleOwner vehicleOwner = driverDetail.getVehicleOwner();
        Validate.notNull(vehicleOwner, "Vehicle owner is required.");

        DriverDetail saved = driverRepository.saveAndFlush(driverDetail);
        return DriverAdaptor.adapt(saved);
    }

}
