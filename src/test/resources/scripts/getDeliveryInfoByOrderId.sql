INSERT INTO public.deliverymen (id, age, name, surname, wages)
VALUES (1, 22, 'John', 'Silver', 22);
INSERT INTO public.clients (id, address, name, phone_number)
VALUES (1, 'Main Street', 'John', '+380932930');
INSERT INTO public.orders (id, bonus, description, payment_option, price, client_id)
VALUES (1, 3, '2 pizza', 'VISA', 50, 1);
INSERT INTO public.cars (id, car_status, colour, model, deliveryman_id)
VALUES (1, 'BUSY', 'black', 'BMW', 1);
INSERT INTO public.calls (id, call_status, deliveryman_id, order_id)
VALUES (1, 'WAITING', 1, 1);