/**
 * Autogenerated by Thrift Compiler (0.15.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package cpnode;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.15.0)", date = "2022-02-19")
public class InvalidInput extends org.apache.thrift.TException implements org.apache.thrift.TBase<InvalidInput, InvalidInput._Fields>, java.io.Serializable, Cloneable, Comparable<InvalidInput> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("InvalidInput");

  private static final org.apache.thrift.protocol.TField WHAT_FIELD_DESC = new org.apache.thrift.protocol.TField("what", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField WHY_FIELD_DESC = new org.apache.thrift.protocol.TField("why", org.apache.thrift.protocol.TType.STRING, (short)2);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new InvalidInputStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new InvalidInputTupleSchemeFactory();

  public int what; // required
  public @org.apache.thrift.annotation.Nullable java.lang.String why; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    WHAT((short)1, "what"),
    WHY((short)2, "why");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // WHAT
          return WHAT;
        case 2: // WHY
          return WHY;
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
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __WHAT_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.WHAT, new org.apache.thrift.meta_data.FieldMetaData("what", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.WHY, new org.apache.thrift.meta_data.FieldMetaData("why", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(InvalidInput.class, metaDataMap);
  }

  public InvalidInput() {
  }

  public InvalidInput(
    int what,
    java.lang.String why)
  {
    this();
    this.what = what;
    setWhatIsSet(true);
    this.why = why;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public InvalidInput(InvalidInput other) {
    __isset_bitfield = other.__isset_bitfield;
    this.what = other.what;
    if (other.isSetWhy()) {
      this.why = other.why;
    }
  }

  public InvalidInput deepCopy() {
    return new InvalidInput(this);
  }

  @Override
  public void clear() {
    setWhatIsSet(false);
    this.what = 0;
    this.why = null;
  }

  public int getWhat() {
    return this.what;
  }

  public InvalidInput setWhat(int what) {
    this.what = what;
    setWhatIsSet(true);
    return this;
  }

  public void unsetWhat() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __WHAT_ISSET_ID);
  }

  /** Returns true if field what is set (has been assigned a value) and false otherwise */
  public boolean isSetWhat() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __WHAT_ISSET_ID);
  }

  public void setWhatIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __WHAT_ISSET_ID, value);
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getWhy() {
    return this.why;
  }

  public InvalidInput setWhy(@org.apache.thrift.annotation.Nullable java.lang.String why) {
    this.why = why;
    return this;
  }

  public void unsetWhy() {
    this.why = null;
  }

  /** Returns true if field why is set (has been assigned a value) and false otherwise */
  public boolean isSetWhy() {
    return this.why != null;
  }

  public void setWhyIsSet(boolean value) {
    if (!value) {
      this.why = null;
    }
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case WHAT:
      if (value == null) {
        unsetWhat();
      } else {
        setWhat((java.lang.Integer)value);
      }
      break;

    case WHY:
      if (value == null) {
        unsetWhy();
      } else {
        setWhy((java.lang.String)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case WHAT:
      return getWhat();

    case WHY:
      return getWhy();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case WHAT:
      return isSetWhat();
    case WHY:
      return isSetWhy();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that instanceof InvalidInput)
      return this.equals((InvalidInput)that);
    return false;
  }

  public boolean equals(InvalidInput that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_what = true;
    boolean that_present_what = true;
    if (this_present_what || that_present_what) {
      if (!(this_present_what && that_present_what))
        return false;
      if (this.what != that.what)
        return false;
    }

    boolean this_present_why = true && this.isSetWhy();
    boolean that_present_why = true && that.isSetWhy();
    if (this_present_why || that_present_why) {
      if (!(this_present_why && that_present_why))
        return false;
      if (!this.why.equals(that.why))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + what;

    hashCode = hashCode * 8191 + ((isSetWhy()) ? 131071 : 524287);
    if (isSetWhy())
      hashCode = hashCode * 8191 + why.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(InvalidInput other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.compare(isSetWhat(), other.isSetWhat());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetWhat()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.what, other.what);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetWhy(), other.isSetWhy());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetWhy()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.why, other.why);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  @org.apache.thrift.annotation.Nullable
  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("InvalidInput(");
    boolean first = true;

    sb.append("what:");
    sb.append(this.what);
    first = false;
    if (!first) sb.append(", ");
    sb.append("why:");
    if (this.why == null) {
      sb.append("null");
    } else {
      sb.append(this.why);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class InvalidInputStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public InvalidInputStandardScheme getScheme() {
      return new InvalidInputStandardScheme();
    }
  }

  private static class InvalidInputStandardScheme extends org.apache.thrift.scheme.StandardScheme<InvalidInput> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, InvalidInput struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // WHAT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.what = iprot.readI32();
              struct.setWhatIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // WHY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.why = iprot.readString();
              struct.setWhyIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, InvalidInput struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(WHAT_FIELD_DESC);
      oprot.writeI32(struct.what);
      oprot.writeFieldEnd();
      if (struct.why != null) {
        oprot.writeFieldBegin(WHY_FIELD_DESC);
        oprot.writeString(struct.why);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class InvalidInputTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public InvalidInputTupleScheme getScheme() {
      return new InvalidInputTupleScheme();
    }
  }

  private static class InvalidInputTupleScheme extends org.apache.thrift.scheme.TupleScheme<InvalidInput> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, InvalidInput struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetWhat()) {
        optionals.set(0);
      }
      if (struct.isSetWhy()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetWhat()) {
        oprot.writeI32(struct.what);
      }
      if (struct.isSetWhy()) {
        oprot.writeString(struct.why);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, InvalidInput struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.what = iprot.readI32();
        struct.setWhatIsSet(true);
      }
      if (incoming.get(1)) {
        struct.why = iprot.readString();
        struct.setWhyIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

