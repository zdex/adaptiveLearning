export interface studentDTO {
  id?: string;
  firstName: string;
  lastName: string;
  email?: string;
  parentId?: string;
  age?: number;
  grade?: string;
  dateOfBirth?: string;
}

export interface parentDTO {
  id?: string;
  firstName: string;
  lastName: string;
  email?: string;
  dateOfBirth?: string;
}
