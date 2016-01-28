package no.westerdals.PG4100.innlevering1;


interface ICarRental {
    boolean rentACar(Customer customer);

    void returnACar(Customer customer);

}
