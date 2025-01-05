import {User} from "./User";
import {SubGroup} from "./SubGroup";

export interface Group {
  id: string;
  name: string;
  managers: User[];
  members: User[];
  subGroups: SubGroup[];
}
