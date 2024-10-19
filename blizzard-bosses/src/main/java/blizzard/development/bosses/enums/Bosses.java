package blizzard.development.bosses.enums;

public enum Bosses {
    BIGFOOT(
            "BigFoot",
            "ewogICJ0aW1lc3RhbXAiIDogMTY0MDkwMjg0MTQzNywKICAicHJvZmlsZUlkIiA6ICI2MTZiODhkNDMwNzM0ZTM3OWM3NDc1ODdlZTJkNzlmZCIsCiAgInByb2ZpbGVOYW1lIiA6ICJKZWxseUZpbiIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9jYjcxNmM5NWE5NTdmNDlmZjBlOWNmY2YzNjgyM2M2YjUwM2ViMjQwMjg5MGVkYWI1YzdkNjc2NjVmYWQ5ZWE2IgogICAgfQogIH0KfQ==",
            "MARfrflVIWfvC7fHdjuhGlrrb1vCz4V06m+7puIRrVFrLcEqVGMFr6jSUTTLzoc7jG0CsTdHWz/W/+MRNZrVe1ztWEzhAGYrLbVTlyq6wgbQu1BwU88oliGYZJY/H+dJc0LjaDK0kANARx317ylwAnX4zUS6vO7K1nVLElVPiLjqjFh8WJxH0bpc6J3cOCHsw3uue5COngWQDspEm2S6CazcmQaK8kc68N+8/DFYVuir/anwGGoiERFDFRqZyiYp93PBbFkBF/74gv3kjm+agmxOzrOVAgkx4pZiamxhdmpDf2933lrids++ENjsuot8PI60QsnZ2bfOZkf3DEET0ZFdzbILJ2M02ahGhUuMQlM/u8k7U5yr6CgVRW9P1AJvI1K5fMieSqzDFcuhud8FhsDJrNzl3Gr99hN/7Mfd6X+RuTH0gG6+gdW0ksYAPQYcnZGJreEvN/DDdILj4tb1TRS/dUY0EEtAJQtk/rmynXYdYSRYCjCgDbF72/A1ZYSEaGItIhm8nVSlOcqABycT1ZqnmP+jnlvp8XZd0is+lsMVjsoDLGVBNOycFAIvuh+iF6uQuz6+Kvq1sxhRG4V5GPgMjlgalHMB4YB+f7n5T5WsAPszDr69uESnwPEDpb7gHTTn9yActXYHEK9RaA5cKP4Oj3ngIIyFyq8bE4XjU5E="
    );

    private final String name;
    private final String value;
    private final String signature;

    Bosses(String name, String value, String signature) {
        this.name = name;
        this.value = value;
        this.signature = signature;
    }

    public String getName() {
        return value;
    }

    public String getValue() {
        return value;
    }

    public String getSignature() {
        return signature;
    }
}
