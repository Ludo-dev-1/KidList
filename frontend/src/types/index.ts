export interface Child {
  id: number;
  firstName: string;
  age: number;
}

export interface Gift {
  id: number;
  title: string;
  description: string | null;
  imageUrl: string | null;
  productUrl: string | null;
  reservedBy: string | null;
  reservedAt: string | null;
  childId: number;
}

export type ChildInput = Pick<Child, 'firstName' | 'age'>;
export type GiftInput = Pick<Gift, 'title' | 'description' | 'imageUrl' | 'productUrl'>;
