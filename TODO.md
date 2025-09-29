# To Do's

- [ ] Refactor project struture
  - The goal is to refactor the complete project structure to better follow general React architectures
  - An inspiration would be [BlueSky Social App](https://github.com/bluesky-social/social-app/tree/main/src)
- [ ] Write better documentation about architecture decisions
  - Filename, directory structure and general naming conventions
  - General architecture conventions
  - State management
    - Why using `use-atom`?
    - Why not directly call [zustand](https://github.com/pmndrs/zustand) or [jotai](https://github.com/pmndrs/jotai)?
- [ ] Refactor tests to create macros that would handle all async error messages
  - All tests using `async` with `catch` error on final
  - Write documentation about test helpers
  - How to print DOM for test debug using [prettyDOM](https://testing-library.com/docs/dom-testing-library/api-debugging/#prettydom)
