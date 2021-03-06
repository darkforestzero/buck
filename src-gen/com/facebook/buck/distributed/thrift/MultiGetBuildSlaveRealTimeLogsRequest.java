/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.facebook.buck.distributed.thrift;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2017-02-20")
public class MultiGetBuildSlaveRealTimeLogsRequest implements org.apache.thrift.TBase<MultiGetBuildSlaveRealTimeLogsRequest, MultiGetBuildSlaveRealTimeLogsRequest._Fields>, java.io.Serializable, Cloneable, Comparable<MultiGetBuildSlaveRealTimeLogsRequest> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("MultiGetBuildSlaveRealTimeLogsRequest");

  private static final org.apache.thrift.protocol.TField STAMPEDE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("stampedeId", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField BATCHES_FIELD_DESC = new org.apache.thrift.protocol.TField("batches", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new MultiGetBuildSlaveRealTimeLogsRequestStandardSchemeFactory());
    schemes.put(TupleScheme.class, new MultiGetBuildSlaveRealTimeLogsRequestTupleSchemeFactory());
  }

  public StampedeId stampedeId; // optional
  public List<LogLineBatchRequest> batches; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    STAMPEDE_ID((short)1, "stampedeId"),
    BATCHES((short)2, "batches");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // STAMPEDE_ID
          return STAMPEDE_ID;
        case 2: // BATCHES
          return BATCHES;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final _Fields optionals[] = {_Fields.STAMPEDE_ID,_Fields.BATCHES};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.STAMPEDE_ID, new org.apache.thrift.meta_data.FieldMetaData("stampedeId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, StampedeId.class)));
    tmpMap.put(_Fields.BATCHES, new org.apache.thrift.meta_data.FieldMetaData("batches", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, LogLineBatchRequest.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(MultiGetBuildSlaveRealTimeLogsRequest.class, metaDataMap);
  }

  public MultiGetBuildSlaveRealTimeLogsRequest() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public MultiGetBuildSlaveRealTimeLogsRequest(MultiGetBuildSlaveRealTimeLogsRequest other) {
    if (other.isSetStampedeId()) {
      this.stampedeId = new StampedeId(other.stampedeId);
    }
    if (other.isSetBatches()) {
      List<LogLineBatchRequest> __this__batches = new ArrayList<LogLineBatchRequest>(other.batches.size());
      for (LogLineBatchRequest other_element : other.batches) {
        __this__batches.add(new LogLineBatchRequest(other_element));
      }
      this.batches = __this__batches;
    }
  }

  public MultiGetBuildSlaveRealTimeLogsRequest deepCopy() {
    return new MultiGetBuildSlaveRealTimeLogsRequest(this);
  }

  @Override
  public void clear() {
    this.stampedeId = null;
    this.batches = null;
  }

  public StampedeId getStampedeId() {
    return this.stampedeId;
  }

  public MultiGetBuildSlaveRealTimeLogsRequest setStampedeId(StampedeId stampedeId) {
    this.stampedeId = stampedeId;
    return this;
  }

  public void unsetStampedeId() {
    this.stampedeId = null;
  }

  /** Returns true if field stampedeId is set (has been assigned a value) and false otherwise */
  public boolean isSetStampedeId() {
    return this.stampedeId != null;
  }

  public void setStampedeIdIsSet(boolean value) {
    if (!value) {
      this.stampedeId = null;
    }
  }

  public int getBatchesSize() {
    return (this.batches == null) ? 0 : this.batches.size();
  }

  public java.util.Iterator<LogLineBatchRequest> getBatchesIterator() {
    return (this.batches == null) ? null : this.batches.iterator();
  }

  public void addToBatches(LogLineBatchRequest elem) {
    if (this.batches == null) {
      this.batches = new ArrayList<LogLineBatchRequest>();
    }
    this.batches.add(elem);
  }

  public List<LogLineBatchRequest> getBatches() {
    return this.batches;
  }

  public MultiGetBuildSlaveRealTimeLogsRequest setBatches(List<LogLineBatchRequest> batches) {
    this.batches = batches;
    return this;
  }

  public void unsetBatches() {
    this.batches = null;
  }

  /** Returns true if field batches is set (has been assigned a value) and false otherwise */
  public boolean isSetBatches() {
    return this.batches != null;
  }

  public void setBatchesIsSet(boolean value) {
    if (!value) {
      this.batches = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case STAMPEDE_ID:
      if (value == null) {
        unsetStampedeId();
      } else {
        setStampedeId((StampedeId)value);
      }
      break;

    case BATCHES:
      if (value == null) {
        unsetBatches();
      } else {
        setBatches((List<LogLineBatchRequest>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case STAMPEDE_ID:
      return getStampedeId();

    case BATCHES:
      return getBatches();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case STAMPEDE_ID:
      return isSetStampedeId();
    case BATCHES:
      return isSetBatches();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof MultiGetBuildSlaveRealTimeLogsRequest)
      return this.equals((MultiGetBuildSlaveRealTimeLogsRequest)that);
    return false;
  }

  public boolean equals(MultiGetBuildSlaveRealTimeLogsRequest that) {
    if (that == null)
      return false;

    boolean this_present_stampedeId = true && this.isSetStampedeId();
    boolean that_present_stampedeId = true && that.isSetStampedeId();
    if (this_present_stampedeId || that_present_stampedeId) {
      if (!(this_present_stampedeId && that_present_stampedeId))
        return false;
      if (!this.stampedeId.equals(that.stampedeId))
        return false;
    }

    boolean this_present_batches = true && this.isSetBatches();
    boolean that_present_batches = true && that.isSetBatches();
    if (this_present_batches || that_present_batches) {
      if (!(this_present_batches && that_present_batches))
        return false;
      if (!this.batches.equals(that.batches))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_stampedeId = true && (isSetStampedeId());
    list.add(present_stampedeId);
    if (present_stampedeId)
      list.add(stampedeId);

    boolean present_batches = true && (isSetBatches());
    list.add(present_batches);
    if (present_batches)
      list.add(batches);

    return list.hashCode();
  }

  @Override
  public int compareTo(MultiGetBuildSlaveRealTimeLogsRequest other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetStampedeId()).compareTo(other.isSetStampedeId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStampedeId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.stampedeId, other.stampedeId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBatches()).compareTo(other.isSetBatches());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBatches()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.batches, other.batches);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("MultiGetBuildSlaveRealTimeLogsRequest(");
    boolean first = true;

    if (isSetStampedeId()) {
      sb.append("stampedeId:");
      if (this.stampedeId == null) {
        sb.append("null");
      } else {
        sb.append(this.stampedeId);
      }
      first = false;
    }
    if (isSetBatches()) {
      if (!first) sb.append(", ");
      sb.append("batches:");
      if (this.batches == null) {
        sb.append("null");
      } else {
        sb.append(this.batches);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (stampedeId != null) {
      stampedeId.validate();
    }
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class MultiGetBuildSlaveRealTimeLogsRequestStandardSchemeFactory implements SchemeFactory {
    public MultiGetBuildSlaveRealTimeLogsRequestStandardScheme getScheme() {
      return new MultiGetBuildSlaveRealTimeLogsRequestStandardScheme();
    }
  }

  private static class MultiGetBuildSlaveRealTimeLogsRequestStandardScheme extends StandardScheme<MultiGetBuildSlaveRealTimeLogsRequest> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, MultiGetBuildSlaveRealTimeLogsRequest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // STAMPEDE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.stampedeId = new StampedeId();
              struct.stampedeId.read(iprot);
              struct.setStampedeIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // BATCHES
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list114 = iprot.readListBegin();
                struct.batches = new ArrayList<LogLineBatchRequest>(_list114.size);
                LogLineBatchRequest _elem115;
                for (int _i116 = 0; _i116 < _list114.size; ++_i116)
                {
                  _elem115 = new LogLineBatchRequest();
                  _elem115.read(iprot);
                  struct.batches.add(_elem115);
                }
                iprot.readListEnd();
              }
              struct.setBatchesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, MultiGetBuildSlaveRealTimeLogsRequest struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.stampedeId != null) {
        if (struct.isSetStampedeId()) {
          oprot.writeFieldBegin(STAMPEDE_ID_FIELD_DESC);
          struct.stampedeId.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.batches != null) {
        if (struct.isSetBatches()) {
          oprot.writeFieldBegin(BATCHES_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.batches.size()));
            for (LogLineBatchRequest _iter117 : struct.batches)
            {
              _iter117.write(oprot);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class MultiGetBuildSlaveRealTimeLogsRequestTupleSchemeFactory implements SchemeFactory {
    public MultiGetBuildSlaveRealTimeLogsRequestTupleScheme getScheme() {
      return new MultiGetBuildSlaveRealTimeLogsRequestTupleScheme();
    }
  }

  private static class MultiGetBuildSlaveRealTimeLogsRequestTupleScheme extends TupleScheme<MultiGetBuildSlaveRealTimeLogsRequest> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, MultiGetBuildSlaveRealTimeLogsRequest struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetStampedeId()) {
        optionals.set(0);
      }
      if (struct.isSetBatches()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetStampedeId()) {
        struct.stampedeId.write(oprot);
      }
      if (struct.isSetBatches()) {
        {
          oprot.writeI32(struct.batches.size());
          for (LogLineBatchRequest _iter118 : struct.batches)
          {
            _iter118.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, MultiGetBuildSlaveRealTimeLogsRequest struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.stampedeId = new StampedeId();
        struct.stampedeId.read(iprot);
        struct.setStampedeIdIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list119 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.batches = new ArrayList<LogLineBatchRequest>(_list119.size);
          LogLineBatchRequest _elem120;
          for (int _i121 = 0; _i121 < _list119.size; ++_i121)
          {
            _elem120 = new LogLineBatchRequest();
            _elem120.read(iprot);
            struct.batches.add(_elem120);
          }
        }
        struct.setBatchesIsSet(true);
      }
    }
  }

}

