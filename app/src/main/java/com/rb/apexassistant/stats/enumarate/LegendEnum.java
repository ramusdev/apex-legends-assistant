package com.rb.apexassistant.stats.enumarate;

public enum LegendEnum {
    REVENANT("Revenant"),
    CRYPTO("Crypto"),
    HORIZON("Horizon"),
    GIBRALTAR("Gibraltar"),
    WATTSON("Wattson"),
    FUSE("Fuse"),
    BANGALORE("Bangalore"),
    WRAITH("Wraith"),
    OCTANE("Octane"),
    BLOODHOUND("Bloodhound"),
    CAUSTIC("Caustic"),
    LIFELINE("Lifeline"),
    PATHFINDER("Pathfinder"),
    LOBA("Loba"),
    MIRAGE("Mirage"),
    RAMPART("Rampart"),
    VALKYRIE("Valkyrie"),
    SEER("Seer"),
    ASH("Ash"),
    MADMAGGIE("Mad Maggie");

    private String name;

    private LegendEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
