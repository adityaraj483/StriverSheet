import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GooglePrevious2 {
    //1. Your organization has hired interns who need to relocate for the summer.
    // You are in charge of assigning apartments to them. Each intern will get their own room.
    // They can choose whether they prefer to share a 2+ room apartment or get a one-bedroom to themselves.
    //Note that they may not get what they want because the apartments vary in the number of rooms that they have.

    class solution {
    //        public static void main(String[] args) {
    //            List<Apartment> apartments = new ArrayList<>();
    //            apartments.add(new Apartment(101, 1));
    //            apartments.add(new Apartment(102, 2));
    //            apartments.add(new Apartment(103, 1));
    //            apartments.add(new Apartment(104, 3));
    //
    //
    //            List<Person> people = new ArrayList<>();
    //            people.add(new Person("Jean", true)); // Wants housemates
    //            people.add(new Person("Maria", false)); // Doesn't want housemates
    //            people.add(new Person("Alex", true));
    //            people.add(new Person("Xinyi", true));
    //            people.add(new Person("Filippo", true));
    //            people.add(new Person("Cameron", false));
    //            people.add(new Person("Vikki", false)); // Wants housemates
    //            for (var entry : assignApartmentsToPeople(apartments, people).entrySet()) {
    //                var key = entry.getKey();
    //                System.out.print(key + " : ");
    //                for (String s : entry.getValue()) {
    //                    System.out.print(s + ", ");
    //                }
    //                System.out.println();
    //            }
    //        }


        class Apartment {
            int apartmentNo;
            int bedsCount;

            Apartment(int apartmentNo, int bedsCount) {
                this.apartmentNo = apartmentNo;
                this.bedsCount = bedsCount;
            }
        }

        class Person {
            String name;
            boolean wantRoommate;

            Person(String name, boolean wantRoommate) {
                this.name = name;
                this.wantRoommate = wantRoommate;
            }

        }

        Map<Integer, List<String>> apartmentPersonMap;

        public Map<Integer, List<String>> assignApartmentsToPeople(List<Apartment> apartments, List<Person> persons) {
            List<Apartment> singleRoomApartment = new ArrayList<>();
            List<Apartment> multiRoomApartment = new ArrayList<>();
            for (Apartment apartment : apartments) {
                if (apartment.bedsCount == 1)
                    singleRoomApartment.add(apartment);
                else
                    multiRoomApartment.add(apartment);
            }

            List<Person> wantSingleRoomPerson = new ArrayList<>();
            List<Person> wantMultiRoomPerson = new ArrayList<>();
            for (Person person : persons) {
                if (person.wantRoommate) {
                    wantMultiRoomPerson.add(person);
                } else
                    wantSingleRoomPerson.add(person);
            }

            apartmentPersonMap = new HashMap<>();

            AtomicInteger singleRoomPersonIndex = new AtomicInteger(0);
            AtomicInteger singleRoomApartmentIndex = new AtomicInteger(0);
            assign(singleRoomApartment, singleRoomApartmentIndex, wantSingleRoomPerson, singleRoomPersonIndex);


            AtomicInteger multiRoomPersonIndex = new AtomicInteger(0);
            assign(singleRoomApartment, singleRoomApartmentIndex, wantMultiRoomPerson, multiRoomPersonIndex);

            AtomicInteger multiRoomApartmentIndex = new AtomicInteger(0);
            assign(multiRoomApartment, multiRoomApartmentIndex, wantSingleRoomPerson, singleRoomPersonIndex);

            assign(multiRoomApartment, multiRoomApartmentIndex, wantMultiRoomPerson, multiRoomPersonIndex);


            return apartmentPersonMap;

        }

        void assign(List<Apartment> apartments, AtomicInteger apartmentIndex, List<Person> persons, AtomicInteger personIndex) {
            if (apartments.size() == apartmentIndex.get() || persons.size() == personIndex.get())
                return;


            while (apartmentIndex.get() < apartments.size() && personIndex.get() < persons.size()) {
                Apartment apartment = apartments.get(apartmentIndex.get());
                int cnt = apartment.bedsCount - apartmentPersonMap.getOrDefault(apartment.apartmentNo, new ArrayList<>()).size();
                while (cnt > 0 && personIndex.get() < persons.size()) {
                    Person person = persons.get(personIndex.get());
                    apartmentPersonMap.computeIfAbsent(apartment.apartmentNo, e -> new ArrayList<>()).add(person.name);
                    personIndex.set(personIndex.get() + 1);
                    cnt--;
                }
                if (cnt > 0)
                    break;
                apartmentIndex.set(apartmentIndex.get() + 1);
            }

        }
    }
    //2.: There is stream of float values (-inf, inf) which is coming as input and an integer D.
    //We need to find a set of 3 values which satisfy condition -
    // |a - b| <= D, |b - c| <= D, |a - c| <= D, assuming a,b,c are 3 float values.
    // Print these 3 values and remove them and continue ....
    //Constraints -
    //All values in stream will be unique.
    //D -> [0, inf)
    //Eg:
    //Input stream - [1,10,7,-2,8,....], d = 5
    //Output - (when 8 comes, then print "7 8 10" and remove them and continue)
    //    public static void main(String[] args) {
    //        TreeSet<Double> set = new TreeSet<>();
    //        set.add(1.0);
    //        set.add(10.0);
    //        set.add(7.0);
    //        set.add(-2.0);
    //        set.add(8.0);
    //        checkTriplets(set, 8, 5);
    //    }
    private static void checkTriplets(TreeSet<Double> set, double x, double D) {
        NavigableSet<Double> inRange = set.subSet(x - D, true, x + D, true);

        if (inRange.size() >= 3) {
            Iterator<Double> it = inRange.iterator();
            double a = it.next();
            double b = it.next();
            double c = it.next();

            double min = Math.min(a, Math.min(b, c));
            double max = Math.max(a, Math.max(b, c));

            if (max - min <= D) {
                System.out.println("Triplet: " + a + ", " + b + ", " + c);
                set.remove(a);
                set.remove(b);
                set.remove(c);
            }
        }
    }
    //3.



}
