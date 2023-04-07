package com.example.scanqr;

public   class manu {
    public static class  C {
    private String  name;
    private String description;

    public C(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

        @Override
        public String toString() {
            return "C{" +
                    "name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
 public static class d extends C{
        private String  hee;

        public d(String name, String description, String hee) {
            super(name, description);
            this.hee = hee;
        }

        public String getHee() {
            return hee;
        }

        public void setHee(String hee) {
            this.hee = hee;
        }

     @Override
     public String toString() {
         return super.toString()+ "d{" +
                 "hee='" + hee + '\'' +
                 '}';
     }
 }
    public static void main(String[] args) {
    d x=new d("a","a","a");
        System.out.println();
    }
}
