import { Comment } from './comment.model';

export interface Post {
  id: number;
  title: string;
  content: string;
  userId: number;
  authorName: string;
  topicId: number;
  comments: Comment[];
  createdAt: string;
  updatedAt: string;
  topicName: string;
}

export const DEFAULT_POST: Post = {
  id: 0,
  title: '',
  content: '',
  userId: 0,
  authorName: '',
  topicId: 0,
  comments: [],
  createdAt: '',
  updatedAt: '',
  topicName: '',
};
