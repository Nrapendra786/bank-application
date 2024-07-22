package com.nrapendra.bank.auth;

import java.net.URI;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nrapendra.bank.customer.Customer;
import com.nrapendra.bank.customer.CustomerRequest;
import com.nrapendra.bank.customer.CustomerRepository;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public ResponseEntity<Customer> signup(@RequestBody CustomerRequest newCustomerRequest, UriComponentsBuilder ucb) {
        Customer newCustomer = Customer.builder()
                .email(newCustomerRequest.email())
                .password(newCustomerRequest.password())
                .role(newCustomerRequest.role())
                .build();

        String passordHash = this.passwordEncoder.encode(newCustomer.getPassword());
        newCustomer.setPassword(passordHash);

        Customer savedCustomer = this.customerRepository.save(newCustomer);
        URI savedCustomerLocation = ucb.path("/api/v1/customers/{id}").buildAndExpand(savedCustomer.getId()).toUri();
        return ResponseEntity.created(savedCustomerLocation).body(savedCustomer);
    }

    @GetMapping("/signin")
    public ResponseEntity<Customer> getCurrentUser(Authentication authentication) {
        Optional<Customer> currentUser = customerRepository.findByEmail(authentication.getName());
        if (currentUser.isPresent()) {
            return ResponseEntity.ok(currentUser.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
