package ru.geekbrains.persist;



    public class Company {

        private Long id;

        private String name;

        private String adress;


        public Company() {
        }

        public Company(Long id, String name, String adress) {
            this.id = id;
            this.name = name;
            this.adress = adress;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAdress() {
            return adress;
        }

        public void setAdress(String adress) {
            this.adress = adress;
        }

    }


