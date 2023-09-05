export interface Supplier {
  _id: string;
  name: string;
  uin: string;
  pdv?: number;
  phoneNumber: string;
  contactPerson?: string;
  email: string;
  dateOfStart?: Date;
  materials?: Material[];
}

export interface Material {
  _id: string;
  name: string;
  quantity: number;
  minQuantity: number;
  price: number;
  unitOfMeasure: string;
  isUsed: boolean;
  supplier: Supplier;
}
export interface MaterialList {
  _id: string;
  name: string;
}
export interface Process {
  _id: string;
  name: string;
  startDate: string;
  endDate: string;
  price: string;
  products?: [];
  productionProcessItems?: [];
}

export interface Product {
  _id: string;
  name: string;
  profitMargin: string;
  picUrl: string;
  price: number;
  productionProcess: string;
}
export interface User {
  _id: string;
  userName: string;
  role: string;
}
