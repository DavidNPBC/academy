package com.ctw.workstation.rackasset.entity;

import com.ctw.workstation.rack.entity.Rack;
import com.ctw.workstation.shared.attributes.SharedAttributes;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "T_RACK_ASSET")
public class RackAsset extends SharedAttributes {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rackAssetSequence")
    @SequenceGenerator(name = "rackAssetSequence", sequenceName = "rackAsset_sq")
    private Long id;

    @Column(name = "asset_tag", nullable = false)
    private String assetTag;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "rack_id")
    private Rack rack;

    public void setId(Long id) {
        this.id = id;
    }

    public void setAssetTag(String assetTag) {
        this.assetTag = assetTag;
    }

    public void setRack(Rack rack) {
        this.rack = rack;
    }

    public Long getId() {
        return id;
    }

    public String getAssetTag() {
        return assetTag;
    }

    public Rack getRack() {
        return rack;
    }
}
