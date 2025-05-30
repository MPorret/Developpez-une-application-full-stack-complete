export interface User {
    id: number,
    name: string,
    email: string,
    password: string,
    created_at: Date,
    updated_at: Date
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