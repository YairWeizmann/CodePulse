-- Create repositories first: pull requests reference them.
CREATE TABLE public.repositories (
                                     id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                     github_id bigint NOT NULL,
                                     owner text NOT NULL,
                                     name text NOT NULL,

                                     CONSTRAINT repositories_github_id_unique
                                         UNIQUE (github_id)
);

CREATE TABLE public.pull_requests (
                                      id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                      repository_id bigint NOT NULL,
                                      pr_number integer NOT NULL,
                                      pr_title text NOT NULL,
                                      state text NOT NULL,
                                      latest_commit_sha text NOT NULL,

                                      CONSTRAINT pull_requests_repo_number_unique
                                          UNIQUE (repository_id, pr_number),

                                      CONSTRAINT pull_requests_repository_fk
                                          FOREIGN KEY (repository_id)
                                              REFERENCES public.repositories (id),

                                      CONSTRAINT pull_requests_state_check
                                          CHECK (state IN ('open', 'closed'))
);

CREATE TABLE public.ai_reviews (
                                   id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                   pull_request_id bigint NOT NULL,
                                   commit_sha text NOT NULL,
                                   feedback text NOT NULL,
                                   created_at timestamp with time zone NOT NULL
                                       DEFAULT CURRENT_TIMESTAMP,

                                   CONSTRAINT "ai_reviews.pull_request_id"
                                       FOREIGN KEY (pull_request_id)
                                           REFERENCES public.pull_requests (id)
);