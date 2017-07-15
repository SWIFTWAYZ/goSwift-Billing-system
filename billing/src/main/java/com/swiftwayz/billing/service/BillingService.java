package com.swiftwayz.billing.service;

import com.swiftwayz.billing.repository.BillRepository;
import com.swiftwayz.billing.service.reset.DriverRestService;
import com.swiftwayz.billing.service.reset.ProductRestService;
import com.swiftwayz.domain.billing.Bill;
import com.swiftwayz.domain.billing.Trip;
import com.swiftwayz.domain.user.Driver;
import com.swiftwayz.domain.vehicle.Product;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Created by sydney on 2017/05/29.
 */
@Component
public class BillingService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private ProductRestService productRestService;

    @Autowired
    private DriverRestService driverRestService;

    public Bill createBill(Bill bill){

        // Trip
        Trip trip = bill.getTrip();
        Validate.notNull(trip, "Trip data cannot is required.");

        validateDriver(bill.getDriverId());
        populateProduct(bill);

        validateUser(bill.getUserId());
        //Get base fare
        BigDecimal baseFare = new BigDecimal(4.5);
        bill.setBaseFare(baseFare);

        // Calculate normal fare
        // Normal fare = km *
        BigDecimal normalFare = new BigDecimal(55.6);
        bill.setNormalFare(normalFare);

        // Calculate subtotal
        // subtotal = km *
        BigDecimal subTotal = new BigDecimal(60.81);
        bill.setSubTotalFare(subTotal);

        // Calculate total
        // Total =
        BigDecimal total = new BigDecimal(60);
        bill.setTotalFare(total);

        Bill savedBill = billRepository.save(bill);

        return savedBill;
    }

    private void validateUser(Long userId) {
        Validate.notNull(userId,"User Id is required.");
    }

    private void populateProduct(Bill bill) {
        Product product = productRestService.getProduct(bill.getProduct());
        bill.setProduct(product);
    }

    private void validateDriver(Long driverId) {
        Validate.notNull(driverId, "Driver is required.");
        driverRestService.getDriver(driverId);
    }
}
