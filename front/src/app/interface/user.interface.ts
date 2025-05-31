import { Topic } from "./topic.interface";

export interface User {
    id: number,
    token: string,
    name: string,
    email: string,
    created_at: Date,
    updated_at: Date,
    topics : Topic[],
}

export interface RegisterDTO {
    name: string,
    email: string,
    password: string,
}

export interface LoginDTO {
    username: string,
    password: string,
}